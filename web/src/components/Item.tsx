import { Box, BoxProps, Image } from '@mantine/core';
import { ItemMeta } from '../types';
import { PixelScaling } from './PixelScaling';
import { forwardRef } from 'react';
import MissingTexture from '../assets/texture/missing.png';
import { basePath } from '../util';

export function textureFromItem(item: string) {
	// return '/EchoesOfTheElders/item/' + item + '.png';
	return `${basePath}/item/${item}.png?raw=true/`;
}

export function itemToKey(item: string) {
	return `item.echoes_of_the_elders.${item}`;
}

export const ITEM_SIZE = 16;

export const Item = forwardRef(
	({ item, ...rest }: BoxProps & { item: ItemMeta }, ref) => {
		return (
			<ItemTexture
				src={textureFromItem(item.texture)}
				ref={ref}
				{...rest}
			/>
		);
	}
);

export const ItemTexture = forwardRef(
	({ src, ...rest }: { src: string } & BoxProps, ref) => {
		const { scaling } = PixelScaling.use();

		const itemSize = ITEM_SIZE * scaling;

		return (
			<Box w={itemSize} h={itemSize} {...rest} ref={ref as any}>
				<Image
					src={src}
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
