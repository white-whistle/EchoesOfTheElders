import { Image } from '@mantine/core';
import { useMemo } from 'react';
import { PixelScaling } from './PixelScaling';
import styles from './MCGlyphIcon.module.css';

export function fontToImageSrc(font: string) {
	const tex = font.replace('echoes_of_the_elders:', '');
	return '/font/' + tex + '.png';
}

export const MCGlyphIcon = ({ font }: { font: string }) => {
	const src = useMemo(() => fontToImageSrc(font), [font]);
	const { scaling } = PixelScaling.use();

	return (
		<Image
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
			}}
		/>
	);
};
