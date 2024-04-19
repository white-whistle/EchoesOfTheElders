import { Vertical } from '../Layout';
import { MCGui, MCGuiTitle } from '../components/MCGui';
import { MCText } from '../components/MCText';
import { PixelScaling } from '../components/PixelScaling';

function GettingStarted() {
	const { px } = PixelScaling.use();

	return (
		<Vertical w='100%' align='center'>
			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)}>
					<MCGuiTitle>Getting Started</MCGuiTitle>

					<MCText>hello world</MCText>
				</Vertical>
			</MCGui>
		</Vertical>
	);
}

export default GettingStarted;
