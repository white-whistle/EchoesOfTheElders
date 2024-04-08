import { Box, BoxProps } from '@mantine/core';
import { Item, ItemMeta } from './Item';

export const ItemGrid = ({
	items,
	...rest
}: BoxProps & { items: ItemMeta[] }) => {
	return (
		<Box
			{...rest}
			display='grid'
			style={{
				gridAutoFlow: 'row',
				gap: '10px',
				gridTemplateColumns: 'repeat(auto-fill, 200px)',
			}}
		>
			{items.map((item, index) => (
				<Item key={item.item} index={index} item={item} />
			))}
		</Box>
	);
};
