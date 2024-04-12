import React from 'react';
import { ITEM_META } from '../itemMeta';
import { ItemMeta } from '../types';

export function useItem(itemId: string | undefined) {
	return React.useMemo(
		() => ITEM_META.items.find((item) => item.item === itemId) as ItemMeta,
		[itemId]
	);
}
