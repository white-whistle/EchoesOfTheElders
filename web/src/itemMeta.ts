import ITEM_META from '../../scripts/dist/artifactItemMetadata.json';
import { ItemMeta } from './types';

const itemBlacklist = [
	"spirit_spawn_egg",
	"elderman_spawn_egg",
	"zombee_spawn_egg",
	"raid_totem_egg",
	"raid_debug_item",
]

ITEM_META.items = ITEM_META.items.filter(item => !itemBlacklist.includes(item.item))

export const Items = Object.fromEntries(ITEM_META.items.map(item => [item.item, item])) as unknown as { [key in (typeof ITEM_META.items)[number]['item']]: ItemMeta }

export { ITEM_META }