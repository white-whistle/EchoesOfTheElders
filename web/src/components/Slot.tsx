import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import { PixelScaling } from './PixelScaling';
import styles from './Slot.module.css';
import { ITEM_SIZE } from './Item';
import { ComponentProps } from 'react';
import SlotBG from '../assets/texture/slot.png';
import UberSlotBG from '../../../src/main/resources/assets/echoes_of_the_elders/textures/gui/sprites/widget/star_knob.png';

export const SLOT_SIZE = 18;

export const Slot = ({
	style,
	shade = false,
	slotSize = SLOT_SIZE,
	slotBg = SlotBG,
	...rest
}: PolymorphicComponentProps<'div', BoxComponentProps> & {
	shade?: boolean;
	slotSize?: number;
	slotBg?: string;
}) => {
	const { scaling } = PixelScaling.use();

	const scaledSlotSize = slotSize * scaling;
	const scaledItemSize = ITEM_SIZE * scaling;
	const padding = (scaledSlotSize - scaledItemSize) / 2;

	return (
		<Box
			className={styles.slot}
			w={scaledSlotSize}
			h={scaledSlotSize}
			p={padding}
			bg={`url("${slotBg}")`}
			bgsz='100% 100%'
			style={{
				cursor: rest.onClick ? 'pointer' : undefined,
				'--slot-shade': Number(shade),
				...style,
			}}
			{...rest}
		/>
	);
};

export const UberSlot = (props: ComponentProps<typeof Slot>) => {
	return <Slot {...props} slotSize={32} slotBg={UberSlotBG} />;
};
