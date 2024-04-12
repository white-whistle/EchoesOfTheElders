import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import styles from './MCGui.module.css';
import { PixelScaling } from './PixelScaling';

export const MCGui = (
	props: PolymorphicComponentProps<'div', BoxComponentProps>
) => {
	const { scaling } = PixelScaling.use();

	return (
		<Box
			className={styles.gui}
			style={{
				borderImageWidth: `${scaling * 4}px`,
				padding: scaling * 4,
			}}
			{...props}
		/>
	);
};
