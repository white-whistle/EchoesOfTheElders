import { TextInput, TextInputProps } from '@mantine/core';
import { PixelScaling } from './PixelScaling';

import classes from './MCTextInput.module.css';

export const MCTextInput = ({ style, ...props }: TextInputProps) => {
	const { scaling } = PixelScaling.use();
	return (
		<TextInput
			style={{ '--pixel-scaling': `${scaling}px`, ...style }}
			classNames={classes}
			{...props}
		/>
	);
};
