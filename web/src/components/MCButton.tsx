import { Button, ButtonProps, PolymorphicComponentProps } from '@mantine/core';
import styles from './MCButton.module.css';
import { PixelScaling } from './PixelScaling';
import { forwardRef } from 'react';

const Impl = <C = 'button',>(
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
			{...(rest as any)}
		/>
	);
};

export const MCButton = forwardRef(Impl as any) as unknown as typeof Impl;
