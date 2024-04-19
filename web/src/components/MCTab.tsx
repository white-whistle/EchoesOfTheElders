import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import styles from './MCTab.module.css';
import { PixelScaling } from './PixelScaling';
import clsx from 'clsx';
import { forwardRef } from 'react';

export const MCTab = forwardRef(
	(
		{
			selected,
			...rest
		}: PolymorphicComponentProps<'div', BoxComponentProps> & {
			selected?: boolean;
		},
		ref
	) => {
		const { scaling } = PixelScaling.use();

		return (
			<Box
				{...rest}
				ref={ref as any}
				className={clsx(styles.tab, selected && styles.selected)}
				style={{
					'--pixel-scaling': scaling,
				}}
			/>
		);
	}
);
