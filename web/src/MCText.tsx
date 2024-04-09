import { Text, TextProps } from '@mantine/core';

export const MCText = ({ style, ...rest }: TextProps) => {
	return (
		<Text
			style={{
				fontFamily: 'Minecraft',
				fontSize: '40px',
				fontSmooth: 'never',
				textShadow: '4px 4px 0 #302f2f',
				...style,
			}}
			{...rest}
		/>
	);
};
