import { Box, BoxProps } from '@mantine/core';
import { Item, ItemMeta } from './Item';

export const ItemGrid = ({
	items,
	itemSize,
	...rest
}: BoxProps & { items: ItemMeta[]; itemSize: number }) => {
	return (
		<Box
			{...rest}
			display='grid'
			style={{
				gridAutoFlow: 'row',

				gridTemplateColumns: `repeat(auto-fill, ${itemSize}px)`,
			}}
		>
			{items.map((item) => (
				<Item key={item.item} item={item} w={itemSize} h={itemSize} />
			))}
		</Box>
	);
};
