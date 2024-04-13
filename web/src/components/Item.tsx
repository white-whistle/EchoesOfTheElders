import { Box, BoxProps, Image } from '@mantine/core';
import { ItemMeta } from '../types';
import { PixelScaling } from './PixelScaling';
import { forwardRef } from 'react';
import MissingTexture from '../assets/texture/missing.png';

export function textureFromItem(item: string) {
	return '/item/' + item + '.png';
}

export function itemToKey(item: string) {
	return `item.echoes_of_the_elders.${item}`;
}

export const ITEM_SIZE = 16;

export const Item = forwardRef(
	({ item, ...rest }: BoxProps & { item: ItemMeta }, ref) => {
		const { scaling } = PixelScaling.use();

		const itemSize = ITEM_SIZE * scaling;

		return (
			<Box w={itemSize} h={itemSize} {...rest} ref={ref}>
				<Image
					src={textureFromItem(item.texture)}
					fallbackSrc={MissingTexture}
					w='100%'
					h='100%'
					style={{
						imageRendering: 'pixelated',
					}}
				/>
			</Box>
		);
	}
);
