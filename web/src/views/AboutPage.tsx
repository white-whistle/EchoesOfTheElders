import { Anchor } from '@mantine/core';
import { Vertical } from '../Layout';
import { MCGlyphIcon } from '../components/MCGlyphIcon';
import { MCGui, MCGuiText, MCGuiTitle } from '../components/MCGui';
import { PixelScaling } from '../components/PixelScaling';

function AboutPage() {
	const { px } = PixelScaling.use();

	return (
		<Vertical w='100%' align='center'>
			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)}>
					<MCGuiTitle>About</MCGuiTitle>
					<MCGuiText>
						The motivation behind this mod is to create an alternate
						play-path via giving access to powerful abilities,
						throwing balance out of the window in the process
					</MCGuiText>
					<MCGuiText>
						Too many mods try to balance things, we basically try to
						do the opposite
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Modpack Creators</MCGuiTitle>
					<MCGuiText>
						Feel free to include this mod in your modpack{' '}
						<MCGlyphIcon font='echoes_of_the_elders:heart' />
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Credit</MCGuiTitle>
					<MCGuiText>
						We give credit to all the minecraft mods with an
						open-source code base, We wish to give back to the
						community by continuing the open-source chain
					</MCGuiText>
					<MCGuiText />
					<MCGuiText>
						Minecraft Font - Public domain{' '}
						<Anchor style={{ fontSize: 'inherit' }}>
							https://www.fontspace.com/minecraft-font-f28180
						</Anchor>
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Copyrights</MCGuiTitle>
					<MCGuiText>
						All of the assets in this mod are original and
						protected, and you are not allowed to use them outside
						of this mod
					</MCGuiText>
				</Vertical>
			</MCGui>
		</Vertical>
	);
}

export default AboutPage;
