import { Link } from 'wouter';
import { MCGui, MCGuiText, MCGuiTitle } from '../components/MCGui';
import { MCText } from '../components/MCText';
import { Grid, Image } from '@mantine/core';
import { MC_PALETTE } from '../logic/MCPalette';
import { Horizontal, Vertical } from '../Layout';
import { PixelScaling } from '../components/PixelScaling';
import { ItemMeta } from '../types';
import { UberSlot } from '../components/Slot';
import { Item } from '../components/Item';
import { MCTooltipPanel } from '../components/MCTooltip';
import { BasicTooltip } from '../components/BasicTooltip';
import { Items } from '../itemMeta';
import ScrollContainer from '../components/ScrollContainer';
import { FormattedMessage } from 'react-intl';
import { MCButton } from '../components/MCButton';
import { MCGlyphIcon, MCGlyphIconBase } from '../components/MCGlyphIcon';
import ExternalIcon from '../assets/texture/external.png';
import { useMediaQuery } from '@mantine/hooks';

import showoff1 from '../assets/screenshots/showoff1.png';
import doomstick from '../assets/screenshots/doomstick.png';
import totem_spawn from '../assets/screenshots/totem_spawn.png';

const philosophy = `\
<star></star> Unique<DARK_GRAY> - Relics have unique effects and interactions</DARK_GRAY>
<explosion></explosion> Unapologetic<DARK_GRAY> - Relics are not afraid to be unbalanced</DARK_GRAY>
<on_hit></on_hit> Powerful<DARK_GRAY> - Receiving items should feel instantly rewarding</DARK_GRAY>
<unique></unique> Clean<DARK_GRAY> -  No ammo, No durability, No boring ingredients</DARK_GRAY>
<heart></heart> Coexist<DARK_GRAY> - Relics aim to not make other items obsolete</DARK_GRAY>
<prism></prism> Pretty<DARK_GRAY> - GPU goes brrr</DARK_GRAY>\
`;

export const Home = () => {
	const { px } = PixelScaling.use();
	const isSM = useMediaQuery(`(max-width: 1250px)`);
	const isMD = useMediaQuery(`(max-width: 1900px)`);

	return (
		<Vertical align='center' gap={px(48)}>
			<Vertical align='center'>
				<MCGui>
					<Vertical gap='0px' p={px(4)}>
						<MCGuiTitle>Echoes of the Elders</MCGuiTitle>
						<MCGuiText>An opinionated relic mod</MCGuiText>
					</Vertical>
				</MCGui>

				<ScrollContainer>
					<Vertical gap='0px' p={`${px(1)} ${px(4)}`}>
						<MCGuiTitle>Philosophy</MCGuiTitle>
						<MCGuiTitle></MCGuiTitle>
						<MCGuiTitle
							style={{ whiteSpace: 'pre' }}
							shadowColor='transparent'
						>
							<FormattedMessage
								id='#invalid'
								defaultMessage={philosophy}
							/>
						</MCGuiTitle>
					</Vertical>
				</ScrollContainer>
				<MCGui>
					<Vertical gap={px(4)} p={px(4)} align='center'>
						<MCGuiText>
							Learn about our core mechanics and progression
						</MCGuiText>
						<MCButton component={Link} to='/getting_started'>
							<MCText>Take me there!</MCText>
						</MCButton>
					</Vertical>
				</MCGui>
			</Vertical>

			<Vertical align='center'>
				<MCText c={MC_PALETTE.light_purple}>
					... Some of our cool items ...
				</MCText>
				<Grid columns={isSM ? 4 : isMD ? 8 : 12}>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.godslayer} />
					</Grid.Col>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.spiral_sword} />
					</Grid.Col>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.starfall_bow} />
					</Grid.Col>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.elder_prism} />
					</Grid.Col>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.pocket_galaxy} />
					</Grid.Col>
					<Grid.Col span={4}>
						<ItemSpotlight item={Items.orb_of_annihilation} />
					</Grid.Col>
				</Grid>

				<MCGui>
					<Vertical align='center' gap={0} p={px(4)}>
						<MCGuiTitle>More items</MCGuiTitle>
						<MCGuiText>
							Explore all items in our item gallery
						</MCGuiText>
						<MCGuiText />
						<MCButton
							component={Link}
							to='/items'
							style={{ height: 'fit-content' }}
						>
							<MCText>Take me there!</MCText>
						</MCButton>
					</Vertical>
				</MCGui>
			</Vertical>

			<Vertical align='center'>
				<MCText c={MC_PALETTE.light_purple}>
					... Now we're just showing off ...
				</MCText>

				<MCGui>
					<Image src={showoff1} />
				</MCGui>
				<MCGui>
					<Image src={doomstick} />
				</MCGui>
				<MCGui>
					<Image src={totem_spawn} />
				</MCGui>
			</Vertical>

			<Vertical align='center'>
				<MCGui>
					<Vertical align='center' gap={0} p={px(4)}>
						<MCGuiTitle>Love our work?</MCGuiTitle>
						<MCGuiText>
							Star us on github!
							<MCGlyphIcon font='echoes_of_the_elders:star' />
						</MCGuiText>
						<MCGuiText />
						<Horizontal>
							<MCButton
								style={{ height: 'fit-content' }}
								component='a'
								target='_blank'
								href='https://github.com/white-whistle/EchoesOfTheElders'
							>
								<MCText>
									EOTE&nbsp;
									<MCGlyphIconBase src={ExternalIcon} />
								</MCText>
							</MCButton>

							<MCButton
								style={{ height: 'fit-content' }}
								component='a'
								target='_blank'
								href='https://github.com/white-whistle'
							>
								<MCText>
									White Whistle&nbsp;
									<MCGlyphIconBase src={ExternalIcon} />
								</MCText>
							</MCButton>
						</Horizontal>
					</Vertical>
				</MCGui>
			</Vertical>
		</Vertical>
	);
};

const ItemSpotlight = ({ item }: { item: ItemMeta }) => {
	return (
		<Vertical align='center'>
			<UberSlot>
				<Item item={item} />
			</UberSlot>
			<MCTooltipPanel>
				<BasicTooltip item={item} />
			</MCTooltipPanel>
		</Vertical>
	);
};
