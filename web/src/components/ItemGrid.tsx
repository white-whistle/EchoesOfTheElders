import { Box, BoxProps } from '@mantine/core';
import { Item, itemToKey } from './Item';
import { ItemMeta } from '../types';
import { PixelScaling } from './PixelScaling';
import { SLOT_SIZE, Slot } from './Slot';
import { MCFloatingTooltip } from './MCTooltip';
import { Vertical } from '../Layout';
import { MCText } from './MCText';
import { FormattedMessage } from 'react-intl';
import { textMessageIdentifier, textMessageToFmsg } from '../logic/TextMessage';
import { BasicTooltip } from './BasicTooltip';

export const ItemGrid = ({
	items,
	onItemClick,
	...rest
}: BoxProps & {
	items: ItemMeta[];
	onItemClick?: (item: ItemMeta) => void;
}) => {
	const { scaling } = PixelScaling.use();
	const slotSize = scaling * SLOT_SIZE;

	return (
		<Box
			{...rest}
			display='grid'
			style={{
				gridAutoFlow: 'row',

				gridTemplateColumns: `repeat(auto-fill, ${slotSize}px)`,
			}}
		>
			{items.map((item) => (
				<Slot onClick={() => onItemClick?.(item)}>
					<MCFloatingTooltip
						position='right-start'
						label={<BasicTooltip item={item} />}
					>
						<Item key={item.item} item={item} />
					</MCFloatingTooltip>
				</Slot>
			))}
		</Box>
	);
};
