import { useMemo, useState } from 'react';
import { ItemMeta } from '../types';
import { MC_BRIGHT_PALETTE, MC_PALETTE } from '../logic/MCPalette';
import {
	getRewardDropRateForRange,
	getRewardsInLevel,
	globalHighestDropLevel,
	globalLowestDropLevel,
} from './DropRateGraph';
import { PixelScaling } from './PixelScaling';
import { Line } from 'react-chartjs-2';
import { Chart, Filler, Tooltip } from 'chart.js';
import { MCFloatingTooltip } from './MCTooltip';
import { Box } from '@mantine/core';
import { Horizontal, Vertical } from '../Layout';
import { MCText } from './MCText';

Chart.register(Filler, Tooltip);

const COLORS = Object.values(MC_BRIGHT_PALETTE);
const indexToColor = (index: number) => {
	return COLORS[index % COLORS.length];
};

export const DropMapGraph = ({ items }: { items: ItemMeta[] }) => {
	const { scaling } = PixelScaling.use();

	const data = useMemo(() => {
		const acc: { [key: number]: number } = {};

		const datasets = items
			.map((item, index) => {
				const dropMetas = getRewardDropRateForRange(
					item,
					globalLowestDropLevel,
					globalHighestDropLevel
				).map((p) => ({ ...p, chance: p.y, item }));
				if (!dropMetas.length) return null;

				dropMetas.forEach((p) => {
					const { x, y } = p;
					acc[x] ??= 0;
					p.y += acc[x];
					acc[x] += y;
				});

				return {
					data: dropMetas,
					fill: 'stack',
					borderColor: 'transparent',
					radius: 0,
					color: indexToColor(index),
					fillColor: indexToColor(index),
					segment: {
						backgroundColor: indexToColor(index),
					},
				};
			})
			.filter(Boolean);

		return {
			datasets: datasets,
		};
	}, [items]);

	const [tooltipData, setTooltipData] = useState<any>(null);

	const options = useMemo(() => {
		return {
			interaction: {
				intersect: false,
				mode: 'index',
			},
			animation: false,
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
						// stepSize: 10,
						font: {
							size: 10 * scaling,
						},
					},
					type: 'linear',
					min: 0,
				},
				y: {
					display: false,
					title: {
						display: false,
						// text: 'Drop Chance %',
						font: {
							size: 10 * scaling,
						},
					},
					ticks: {
						font: {
							size: 10 * scaling,
						},
						stepSize: 10,
					},
					min: 0,
					max: 120,
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

				tooltip: {
					enabled: false,
					position: 'nearest',
					external: (context: any) => {
						const { tooltip } = context;

						const actualPoints = tooltip.dataPoints
							.filter((p) => p.raw.chance > 0)
							.reverse();

						const levelTotalRewards = getRewardsInLevel(
							+tooltip.title[0]
						).length;

						const remainderDrops = parseFloat(
							(
								100 -
								Math.min(
									100,
									actualPoints.reduce(
										(acc: number, curr: any) =>
											acc + curr.raw.chance,
										0
									)
								)
							).toFixed(1)
						);

						setTooltipData(
							<Vertical gap='0'>
								<MCText>Raid Level {tooltip.title[0]}</MCText>
								<MCText c={MC_PALETTE.gray}>Drops:</MCText>
								{actualPoints.map((p) => {
									return (
										<Horizontal key={p.raw.item.item}>
											<MCText
												c={p.dataset.fillColor}
												shadowColor='transparent'
												w='4ch'
											>
												{p.raw.chance}%
											</MCText>
											<MCText
												c={p.dataset.fillColor}
												shadowColor='transparent'
											>
												{p.raw.item.name}
											</MCText>
										</Horizontal>
									);
								})}
								{levelTotalRewards > actualPoints.length && (
									<Horizontal>
										<MCText
											c={MC_PALETTE.dark_gray}
											shadowColor='transparent'
											w='4ch'
										>
											{remainderDrops}%
										</MCText>
										<MCText
											c={MC_PALETTE.dark_gray}
											shadowColor='transparent'
										>
											Hidden drops (filtered out)
										</MCText>
									</Horizontal>
								)}
							</Vertical>
						);
					},
				},
			},
		};
	}, [scaling]);

	if (!data?.datasets?.length) return null;

	return (
		<MCFloatingTooltip label={tooltipData ?? ''}>
			<Box style={{ height: '100%' }}>
				<Line
					data={data}
					options={options}
					style={{ height: '100%' }}
				/>
			</Box>
		</MCFloatingTooltip>
	);
};
