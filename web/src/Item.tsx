import { Box, BoxProps, Image } from '@mantine/core';
import { Vertical } from './Layout';
import { MCTooltip } from './MCTooltip';
import { useState } from 'react';
import styles from './Item.module.css';
import { MCText } from './MCText';
import { FormattedMessage } from 'react-intl';

export type ItemMeta = {
	item: string;

	dropData?: {
		min: number;
		max: number;
	};

	rarity?: string;
	isReward?: boolean;
};

export function textureFromItem(item: string) {
	return '/item/' + item + '.png';
}

export const Item = ({ item, ...rest }: BoxProps & { item: ItemMeta }) => {
	const [s, setS] = useState(1);

	return (
		<Box
			w='100%'
			h='100%'
			p='calc(1 / 18 * 100%)'
			className={styles.item}
			{...rest}
		>
			<MCTooltip
				position='right-start'
				label={
					<Vertical>
						<MCText>
							<FormattedMessage
								id={`item.echoes_of_the_elders.${item.item}`}
							/>
						</MCText>
						{Boolean(item.dropData) && (
							<Vertical>
								<MCText>Drops from raids:</MCText>
								<MCText>
									Level {item.dropData!.min}~
									{item.dropData!.max}
								</MCText>
							</Vertical>
						)}
					</Vertical>
				}
			>
				<Image
					src={textureFromItem(item.item)}
					w='100%'
					h='100%'
					style={{
						imageRendering: 'pixelated',
					}}
				/>
			</MCTooltip>
		</Box>
	);
};
