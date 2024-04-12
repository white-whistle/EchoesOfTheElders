import { Box, BoxProps } from '@mantine/core';
import { ItemMeta } from '../types';
import { BasicTooltip } from './BasicTooltip';
import { Item } from './Item';
import { MCFloatingTooltip } from './MCTooltip';
import { PixelScaling } from './PixelScaling';
import { SLOT_SIZE, Slot } from './Slot';

export const ItemGrid = ({
	items,
	onItemClick,
	highlight,
	...rest
}: BoxProps & {
	items: ItemMeta[];
	highlight?: ItemMeta[];
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
			{items.map((item) => {
				const isShaded =
					Boolean(highlight?.length) && !highlight?.includes(item);

				return (
					<Slot onClick={() => onItemClick?.(item)} shade={isShaded}>
						<MCFloatingTooltip
							position='right-start'
							label={<BasicTooltip item={item} />}
						>
							<Item key={item.item} item={item} />
						</MCFloatingTooltip>
					</Slot>
				);
			})}
		</Box>
	);
};
