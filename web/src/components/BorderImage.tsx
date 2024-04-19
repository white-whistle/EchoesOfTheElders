import { Image, ImageProps, PolymorphicComponentProps } from '@mantine/core';
import { PixelScaling } from './PixelScaling';

const BorderImage = ({
	style,
	...rest
}: PolymorphicComponentProps<'img', ImageProps>) => {
	const { px } = PixelScaling.use();

	return (
		<Image style={{ border: `${px(1)} solid black`, ...style }} {...rest} />
	);
};

export default BorderImage;
