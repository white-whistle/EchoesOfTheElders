import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import { PixelScaling } from './PixelScaling';
import styles from './Slot.module.css';

export const SLOT_SIZE = 18;

export const Slot = ({
	style,
	shade = false,
	...rest
}: PolymorphicComponentProps<'div', BoxComponentProps> & {
	shade?: boolean;
}) => {
	const { scaling } = PixelScaling.use();

	const slotSize = SLOT_SIZE * scaling;

	return (
		<Box
			className={styles.slot}
			w={slotSize}
			h={slotSize}
			style={{
				cursor: rest.onClick ? 'pointer' : undefined,
				'--slot-shade': Number(shade),
				...style,
			}}
			{...rest}
		/>
	);
};
