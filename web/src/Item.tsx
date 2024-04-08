import { Box, Image } from '@mantine/core';
import { PropsWithChildren } from 'react';

export type ItemMeta = { item: string; min: number; max: number };

export function textureFromItem(item: string) {
	return '/item/' + item + '.png';
}

export const Item = ({
	item,
	index,
}: PropsWithChildren<{ item: ItemMeta; index: number }>) => {
	return (
		<Box bg='teal' w='100%' h='100%'>
			{index}
			<p>{item.item}</p>
			<p>
				{item.min}~{item.max}
			</p>
			<Image src={textureFromItem(item.item)} />
		</Box>
	);
};
