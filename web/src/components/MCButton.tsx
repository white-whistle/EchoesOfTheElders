import { Button, ButtonProps, PolymorphicComponentProps } from '@mantine/core';
import styles from './MCButton.module.css';
import { PixelScaling } from './PixelScaling';
import { forwardRef } from 'react';

export const MCButton = forwardRef(
	<C = 'button',>(
		{ style, ...rest }: PolymorphicComponentProps<C, ButtonProps>,
		ref
	) => {
		const { scaling } = PixelScaling.use();
		return (
			<Button
				ref={ref}
				classNames={{ root: styles.button }}
				style={{
					'--pixel-scaling': scaling,
					height: 'fit-content',
					...style,
				}}
				{...rest}
			/>
		);
	}
);
