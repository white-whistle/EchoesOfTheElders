import { Image, ImageProps, PolymorphicComponentProps } from '@mantine/core';
import { forwardRef, useMemo } from 'react';
import { PixelScaling } from './PixelScaling';
import styles from './MCGlyphIcon.module.css';

export function fontToImageSrc(font: string) {
	const tex = font.replace('echoes_of_the_elders:', '');
	return '/font/' + tex + '.png';
}

export const MCGlyphIcon = forwardRef(
	(
		{
			font,
			style,
			...rest
		}: PolymorphicComponentProps<'img', ImageProps> & { font: string },
		ref
	) => {
		const src = useMemo(() => fontToImageSrc(font), [font]);
		const { scaling } = PixelScaling.use();

		return (
			<Image
				ref={ref}
				src={src}
				w={scaling * 9}
				h={scaling * 9}
				display='inline-block'
				className={styles.glyph}
				data-glyph-id={font}
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
