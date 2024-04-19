import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import styles from './MCGui.module.css';
import { PixelScaling } from './PixelScaling';
import { MCText, MCTextProps } from './MCText';

export const MCGui = ({
	style,
	...props
}: PolymorphicComponentProps<'div', BoxComponentProps>) => {
	const { scaling } = PixelScaling.use();

	return (
		<Box
			className={styles.gui}
			style={{
				borderImageWidth: `${scaling * 4}px`,
				padding: scaling * 4,
				...style,
			}}
			{...props}
		/>
	);
};

export const MCGuiTitle = (props: MCTextProps) => {
	return <MCText c='dark' shadowColor='#9b9b9b' {...props} />;
};

export const MCGuiText = (props: MCTextProps) => {
	return <MCText c='#5f5f5f' shadowColor='#adadad' {...props} />;
};
