import { PolymorphicComponentProps, Text, TextProps } from '@mantine/core';
import { PixelScaling } from './PixelScaling';
import styles from './MCText.module.css';

export const MCText = ({
	style,
	shadowColor = '#302f2f',
	...rest
}: PolymorphicComponentProps<'p', TextProps> & { shadowColor?: string }) => {
	const { scaling } = PixelScaling.use();
	return (
		<Text
			className={styles.text}
			style={{
				'--pixel-scaling': `${scaling}px`,
				'--mc-text-shadow-color': shadowColor,
				...style,
			}}
			{...rest}
		/>
	);
};
