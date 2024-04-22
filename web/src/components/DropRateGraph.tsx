import { ITEM_META } from '../itemMeta';
import { ItemMeta } from '../types';

const MAX_VALUE = 2147483647;

export function getRewardsInLevel(level: number) {
	const rewards = ITEM_META.items.filter((item) => item.isReward);
	const rewardsForLevel = rewards.filter(
		(r) =>
			level >= (r.dropData?.min ?? 0) &&
			level <= (r.dropData?.max ?? MAX_VALUE)
	);

	return rewardsForLevel;
}

export function getRewardDropRate(item: ItemMeta, level: number) {
	if (!item.dropData) return 0;
	if (level < item.dropData.min) return 0;
	if (level > item.dropData.max) return 0;
	const rewards = getRewardsInLevel(level);
	return 1 / rewards.length;
}

export const globalLowestDropLevel = 0;
export const globalHighestDropLevel = ITEM_META.items.reduce((acc, curr) => {
	if (!curr?.dropData?.max) return acc;
	if (curr.dropData?.max === MAX_VALUE) {
		return acc;
	}

	if (curr.dropData?.max > acc) return curr.dropData?.max;

	return acc;
}, 0);

function getDisplayMax(v: number | undefined) {
	if (!v) return globalHighestDropLevel;
	if (v === MAX_VALUE) return globalHighestDropLevel;

	return v;
}

export function getRewardDropRateForRange(
	item: ItemMeta,
	minOverride?: number,
	maxOverride?: number
) {
	if (!item.isReward) return [];

	const min = minOverride ?? item.dropData?.min ?? globalLowestDropLevel;
	const max = maxOverride ?? getDisplayMax(item.dropData?.max);

	const data: { x: number; y: number }[] = [];
	for (let i = min; i <= max; i++) {
		data.push({
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
