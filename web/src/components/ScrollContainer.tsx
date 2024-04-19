import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
} from '@mantine/core';
import ScrollBG from '../assets/texture/scroll.png';
import { PixelScaling } from './PixelScaling';

function ScrollContainer({
	style,
	...props
}: PolymorphicComponentProps<'div', BoxComponentProps>) {
	const { scaling } = PixelScaling.use();

	return (
		<Box
			style={{
				borderImage: `url("${ScrollBG}")`,
				borderImageSlice: '6 8 6 8 fill',
				borderImageWidth: `${scaling * 6}px ${scaling * 8}px`,
				padding: `${scaling * 6}px ${scaling * 8}px`,
				imageRendering: 'pixelated',
				...style,
			}}
			{...props}
		/>
	);
}

export default ScrollContainer;
