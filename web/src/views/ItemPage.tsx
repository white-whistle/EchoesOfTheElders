import { useParams } from 'wouter';
import { Horizontal, Vertical } from '../Layout';
import { Item } from '../components/Item';
import { useItem } from '../hooks/useItem';
import { MCTooltip, MCTooltipPanel } from '../components/MCTooltip';
import { BasicTooltip } from '../components/BasicTooltip';
import { PixelScaling } from '../components/PixelScaling';

export const ItemPage = () => {
	const params = useParams();

	const item = useItem(params.itemId);

	const { px } = PixelScaling.use();

	if (!item) return null;

	return (
		<Horizontal justify='center'>
			<Vertical gap={px(6)} align='center'>
				<Item item={item} />
				<MCTooltipPanel>
					<BasicTooltip item={item} />
				</MCTooltipPanel>
			</Vertical>
		</Horizontal>
	);
};
