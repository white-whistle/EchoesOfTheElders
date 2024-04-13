import { Link, useParams } from 'wouter';
import { Horizontal, Vertical } from '../Layout';
import { Item } from '../components/Item';
import { useItem } from '../hooks/useItem';
import { MCTooltip, MCTooltipPanel } from '../components/MCTooltip';
import { BasicTooltip } from '../components/BasicTooltip';
import { PixelScaling } from '../components/PixelScaling';
import { DropRateGraph } from '../components/DropRateGraph';
import { MCGui } from '../components/MCGui';
import { MCText } from '../components/MCText';
import { Box } from '@mantine/core';
import { MC_PALETTE } from '../logic/MCPalette';
import { MCButton } from '../components/MCButton';

export const ItemPage = () => {
	const params = useParams();

	const item = useItem(params.itemId);

	const { px } = PixelScaling.use();

	if (!item) return null;

	return (
		<Horizontal justify='center'>
			<MCGui>
				<Vertical gap={px(6)} p={px(6)} align='center' pos='relative'>
					<Horizontal>
						<MCTooltip
							label={<MCText>Back to item gallery</MCText>}
						>
							<MCButton
								component={Link}
								to='/items'
								pos='absolute'
								left={px(5)}
							>
								⬅
							</MCButton>
						</MCTooltip>

						<MCText
							c={MC_PALETTE.dark_gray}
							shadowColor={MC_PALETTE.gray}
						>
							Item info
						</MCText>
					</Horizontal>
					<Horizontal justify='center'>
						<Vertical gap={px(6)} p={px(6)} align='center'>
							<Item item={item} />
							<MCTooltipPanel>
								<BasicTooltip item={item} />
							</MCTooltipPanel>
						</Vertical>
						<Box h='350px'>
							<DropRateGraph item={item} />
						</Box>
					</Horizontal>
				</Vertical>
			</MCGui>
		</Horizontal>
	);
};
