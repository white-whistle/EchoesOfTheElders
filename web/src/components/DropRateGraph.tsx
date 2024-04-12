import { Line } from 'react-chartjs-2';
import { ITEM_META } from '../itemMeta';
import { ItemMeta } from '../types';
import { useMemo } from 'react';
import {
	CategoryScale,
	Chart,
	LinearScale,
	PointElement,
	LineElement,
} from 'chart.js';
import { PixelScaling } from './PixelScaling';
import { MC_PALETTE } from '../logic/MCPalette';

Chart.register(CategoryScale, LinearScale, PointElement, LineElement);
Chart.defaults.font = {
	family: 'Minecraft',
	size: 10,
};

export function getRewardsInLevel(level: number) {
	const rewards = ITEM_META.items.filter((item) => item.isReward);
	const rewardsForLevel = rewards.filter(
		(r) =>
			level >= (r.dropData?.min ?? 0) &&
			level <= (r.dropData?.max ?? Number.MAX_VALUE)
	);

	return rewardsForLevel;
}

export function getRewardDropRate(item: ItemMeta, level: number) {
	const rewards = getRewardsInLevel(level);
	return 1 / rewards.length;
}

const globalLowestDropLevel = 0;
const globalHighestDropLevel = ITEM_META.items.reduce((acc, curr) => {
	if (!curr?.dropData?.max) return acc;
	if (curr.dropData?.max === Number.MAX_VALUE) {
		return acc;
	}

	if (curr.dropData?.max > acc) return curr.dropData?.max;

	return acc;
}, 0);

export function getRewardDropRateForRange(item: ItemMeta) {
	const min = item.dropData?.min ?? globalLowestDropLevel;
	const max = item.dropData?.max ?? globalHighestDropLevel;

	const data = [];
	for (let i = min; i <= max; i++) {
		data.push({
			// level: i,
			// chance: parseFloat((getRewardDropRate(item, i) * 100).toFixed(1)),
			x: i,
			y: parseFloat((getRewardDropRate(item, i) * 100).toFixed(1)),
		});
	}

	if (!item.dropData?.max) {
		const last = data[data.length - 1];

		data.push({ x: Infinity, y: last.y });
	}

	return data;
}

export const DropRateGraph = ({ item }: { item: ItemMeta }) => {
	const { scaling } = PixelScaling.use();

	const data = useMemo(() => {
		const dropMetas = getRewardDropRateForRange(item);
		return {
			datasets: [
				{
					data: dropMetas,
					label: 'test',
					borderColor: MC_PALETTE.red,
				},
			],
		};
	}, [item]);

	const options = useMemo(() => {
		return {
			elements: {
				point: {
					borderWidth: 0,
					radius: 10,
					backgroundColor: 'rgba(0,0,0,0)',
				},
			},

			scales: {
				x: {
					display: true,
					title: {
						display: true,
						text: 'Raid Level',
						font: {
							size: 10 * scaling,
						},
					},
					ticks: {
						stepSize: 10,
						font: {
							size: 10 * scaling,
						},
					},
					type: 'linear',
					min: 0,
				},
				y: {
					display: true,
					title: {
						display: true,
						text: 'Drop Chance %',
						font: {
							size: 10 * scaling,
						},
					},
					ticks: {
						font: {
							size: 10 * scaling,
						},
					},
					suggestedMin: 0,
					suggestedMax: 100,
				},
			},

			plugins: {
				legend: {
					labels: {
						// This more specific font property overrides the global property
						font: {
							size: 10 * scaling,
						},
					},
				},
			},
		};
	}, [scaling]);

	return <Line data={data} options={options} style={{ height: '100%' }} />;
};
