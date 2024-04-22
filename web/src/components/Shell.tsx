import { Anchor, AppShell, Box, Image } from '@mantine/core';
import { Link, useLocation } from 'wouter';
import { Horizontal, Vertical } from '../Layout';
import { PixelScaling } from './PixelScaling';
import { Item, ItemTexture } from './Item';
import { Items } from '../itemMeta';
import { MCText } from './MCText';
import { MCTab } from './MCTab';
import { MCFloatingTooltip } from './MCTooltip';
import GithubIcon from '../assets/texture/github.png';
import CurseforgeIcon from '../assets/texture/curseforge.png';
import TechnologyIcon from '../assets/texture/technology.png';
import InfoIcon from '../assets/texture/info.png';
import ExternalIcon from '../assets/texture/external.png';
import { MCGlyphIconBase } from './MCGlyphIcon';
import { useMediaQuery } from '@mantine/hooks';
import { CURRENT_VERSION } from '../versions';

function Shell({ children }) {
	const { px } = PixelScaling.use();
	const isSM = useMediaQuery(`(max-width: 924px)`);

	return (
		<AppShell header={{ height: 120 }} padding='md'>
			<AppShell.Header
				style={{
					border: 0,
					background: 'transparent',
					borderBottom: '1px solid #ffffff24',
					backdropFilter: 'blur(9px)',
				}}
			>
				<Horizontal justify='center' gap={px(5)}>
					<Box
						flex={1}
						display='flex'
						style={{ justifyContent: 'center', alignSelf: 'start' }}
					>
						<Tabs tabs={gameTabs} />
					</Box>

					{!isSM && (
						<Anchor component={Link} to='/'>
							<Image
								src={CURRENT_VERSION.image}
								h='120px'
								w='fit-content'
								p='md'
								ml='auto'
								mr='auto'
								style={{
									filter: 'drop-shadow(2px 4px 6px black)',
								}}
							/>
						</Anchor>
					)}

					<Box
						flex={1}
						display='flex'
						style={{ justifyContent: 'center', alignSelf: 'start' }}
					>
						<Tabs tabs={infoTabs} />
					</Box>
				</Horizontal>
			</AppShell.Header>
			<AppShell.Main
				style={{
					display: 'flex',
					flexDirection: 'column',
					justifyContent: 'stretch',
					alignItems: 'stretch',
				}}
			>
				<Vertical justify='space-between' flex={1}>
					<Vertical>{children}</Vertical>

					<Horizontal justify='center'>
						<MCText
							c='white'
							style={{ fontSize: '10px' }}
							shadowColor='transparent'
						>
							© White Whistle, All rights reserved{' '}
							<Anchor
								component={Link}
								to={'/about'}
								style={{ fontSize: '10px' }}
							>
								more info
							</Anchor>
						</MCText>
					</Horizontal>
				</Vertical>
			</AppShell.Main>
		</AppShell>
	);
}

const gameTabs = [
	{ route: '/', item: Items.explorers_fruit, tooltip: 'Home' },
	{
		route: '/getting_started',
		item: Items.elder_prism,
		tooltip: 'Getting Started',
	},
	{ route: '/items', item: Items.pandoras_bag, tooltip: 'Item Gallery' },
];

const infoTabs = [
	{
		route: '/about',
		icon: <ItemTexture src={InfoIcon} />,
		tooltip: 'About',
	},
	{
		route: '/technology',
		icon: <ItemTexture src={TechnologyIcon} />,
		tooltip: 'Technology',
	},
	{
		route: '/curseforge',
		icon: <ItemTexture src={CurseforgeIcon} />,
		tooltip: 'Curseforge',
		link: 'https://www.curseforge.com/minecraft/mc-mods/echoes-of-the-elders',
	},
	{
		route: '/github',
		icon: <ItemTexture src={GithubIcon} />,
		tooltip: 'Github',
		link: 'https://github.com/white-whistle/EchoesOfTheElders',
	},
];

const Tabs = ({ tabs }) => {
	const [location, setLocation] = useLocation();

	return (
		<Box style={{ alignSelf: 'start', alignItems: 'start' }} display='flex'>
			{tabs.map((tb) => {
				const selected = tb.route === location;
				return (
					<MCFloatingTooltip
						key={tb.route}
						label={
							<MCText>
								{tb.tooltip}
								{tb.link && (
									<span>
										&nbsp;
										<MCGlyphIconBase src={ExternalIcon} />
									</span>
								)}
							</MCText>
						}
					>
						<MCTab
							selected={selected}
							onClick={() => {
								if (tb.link) {
									window.open(tb.link);
								} else {
									setLocation(tb.route);
								}
							}}
						>
							{tb.icon ? tb.icon : <Item item={tb.item} />}
						</MCTab>
					</MCFloatingTooltip>
				);
			})}
		</Box>
	);
};

export default Shell;
