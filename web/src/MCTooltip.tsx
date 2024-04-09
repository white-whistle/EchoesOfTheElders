import { Tooltip, TooltipFloatingProps } from '@mantine/core';

export const MCTooltip = (props: TooltipFloatingProps) => {
	return (
		<Tooltip.Floating
			styles={{
				tooltip: {
					padding: '8px',
					background: 'rgba(16, 0, 16, 0.94)',
					borderRadius: 0,
					border: '4px solid',
					borderImageSlice: 1,
					borderWidth: '4px',
					borderImageSource:
						'linear-gradient(to bottom, rgba(80, 0, 255, 0.31), rgba(40, 0, 127, 0.31))',
					boxShadow:
						'4px 0 0 rgba(16, 0, 16, 0.94), -4px 0 0 rgba(16, 0, 16, 0.94), 0 4px 0 rgba(16, 0, 16, 0.94), 0 -4px 0 rgba(16, 0, 16, 0.94)',
				},
			}}
			{...props}
		/>
	);
};
