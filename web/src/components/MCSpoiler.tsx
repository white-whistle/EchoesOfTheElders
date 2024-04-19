import { ComponentProps, ReactNode, useState } from 'react';
import { MCGui, MCGuiTitle } from './MCGui';
import { Vertical } from '../Layout';
import { PixelScaling } from './PixelScaling';
import { MC_PALETTE } from '../logic/MCPalette';

const MCSoiler = ({
	label,
	...props
}: ComponentProps<typeof MCGui> & { label: ReactNode }) => {
	const [open, setOpen] = useState(false);
	const { px } = PixelScaling.use();

	if (!open) {
		return (
			<MCGui style={{ cursor: 'pointer' }} onClick={() => setOpen(true)}>
				<Vertical p={px(4)} align='center'>
					<MCGuiTitle c={MC_PALETTE.gold}>Spoilers Ahead</MCGuiTitle>
					<MCGuiTitle>{label}</MCGuiTitle>
				</Vertical>
			</MCGui>
		);
	}

	return <MCGui {...props} />;
};

export default MCSoiler;
