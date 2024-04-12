import {
	Box,
	BoxComponentProps,
	PolymorphicComponentProps,
	Tooltip,
	TooltipFloatingProps,
	TooltipProps,
} from '@mantine/core';
import { PixelScaling } from './PixelScaling';

export function useMCTooltipStyle() {
	const { scaling } = PixelScaling.use();

	return {
		tooltip: {
			color: 'white',
			padding: scaling * 2,
			background: 'rgba(16, 0, 16, 0.94)',
			borderRadius: 0,
			border: `${scaling}px solid`,
			borderImageSlice: 1,
			borderImageWidth: `${scaling}px`,
			borderImageSource:
				'linear-gradient(to bottom, rgba(80, 0, 255, 0.31), rgba(40, 0, 127, 0.31))',
			boxShadow: `${scaling}px 0 0 rgba(16, 0, 16, 0.94), -${scaling}px 0 0 rgba(16, 0, 16, 0.94), 0 ${scaling}px 0 rgba(16, 0, 16, 0.94), 0 -${scaling}px 0 rgba(16, 0, 16, 0.94)`,
		},
	};
}

export const MCTooltip = (props: TooltipProps) => {
	return <Tooltip styles={useMCTooltipStyle()} {...props} />;
};

export const MCFloatingTooltip = (props: TooltipFloatingProps) => {
	return <Tooltip.Floating styles={useMCTooltipStyle()} {...props} />;
};

export const MCTooltipPanel = (
	props: PolymorphicComponentProps<'div', BoxComponentProps>
) => {
	return <Box {...props} style={useMCTooltipStyle().tooltip} />;
};
