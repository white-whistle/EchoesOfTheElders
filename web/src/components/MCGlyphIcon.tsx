import { Image, ImageProps, PolymorphicComponentProps } from '@mantine/core';
import { forwardRef, useMemo } from 'react';
import { PixelScaling } from './PixelScaling';
import styles from './MCGlyphIcon.module.css';
import { basePath } from '../util';

export function fontToImageSrc(font: string) {
	const tex = font.replace('echoes_of_the_elders:', '');
	// return '/font/' + tex + '.png';
	return `${basePath}/font/${tex}.png?raw=true`;
}

export const MCGlyphIconBase = forwardRef(
	(
		{ src, style, ...rest }: PolymorphicComponentProps<'img', ImageProps>,
		ref
	) => {
		const { scaling } = PixelScaling.use();

		return (
			<Image
				ref={ref as any}
				src={src}
				w={scaling * 9}
				h={scaling * 9}
				display='inline-block'
				className={styles.glyph}
				style={{
					imageRendering: 'pixelated',
					verticalAlign: 'text-top',
					'--pixel-scaling': scaling,
					...style,
				}}
				{...rest}
			/>
		);
	}
);

export const MCGlyphIcon = forwardRef(
	(
		{
			font,
			...rest
		}: PolymorphicComponentProps<'img', ImageProps> & { font: string },
		ref
	) => {
		const src = useMemo(() => fontToImageSrc(font), [font]);

		return (
			<MCGlyphIconBase
				src={src}
				{...rest}
				ref={ref}
				data-glyph-id={font}
			/>
		);
	}
);
