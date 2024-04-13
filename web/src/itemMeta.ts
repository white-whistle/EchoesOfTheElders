import ITEM_META from '../../scripts/dist/artifactItemMetadata.json';

const itemBlacklist = [
	"spirit_spawn_egg",
	"elderman_spawn_egg",
	"zombee_spawn_egg",
	"raid_totem_egg",
	"raid_debug_item",
]

ITEM_META.items = ITEM_META.items.filter(item => !itemBlacklist.includes(item.item))

export { ITEM_META }