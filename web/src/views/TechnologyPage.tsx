import { Vertical } from '../Layout';
import { MCGui, MCGuiText, MCGuiTitle } from '../components/MCGui';
import { PixelScaling } from '../components/PixelScaling';

function TechnologyPage() {
	const { px } = PixelScaling.use();

	return (
		<Vertical w='100%' align='center'>
			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Technology</MCGuiTitle>
					<MCGuiText>
						This page is just to geek out about how we made this mod
						and website
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Tech-Stack</MCGuiTitle>
					<MCGuiText>Powered by React</MCGuiText>
					<MCGuiText>Styled mantine components</MCGuiText>
					<MCGuiText>Built via Vite</MCGuiText>
					<MCGuiText>Hosted on Github Pages</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Items in web</MCGuiTitle>
					<MCGuiText>
						Our datagen generates metadata of our modded items in
						JSON format.
					</MCGuiText>
					<MCGuiText />
					<MCGuiText>
						Our CI runs datagen on each release and rebuilds this
						meta file alongside the application.
					</MCGuiText>
					<MCGuiText />
					<MCGuiText>
						The application imports this JSON and displays it
						statically. Using some configuration wiring we are also
						able to consume our mod assets within this web app
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Inventory particle system</MCGuiTitle>
					<MCGuiText>
						We basically just implemented a simple particle system,
						whenever a gui is opened we start to tick the particle
						system and display it to the screen using the same
						handles the screen uses to render. and when it closes we
						just clear the particles
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Icons in tooltip</MCGuiTitle>
					<MCGuiText>
						We declare a new font for each icon we want to render.
					</MCGuiText>
					<MCGuiText>
						Then we render some text and prepend to that text
						another Text object, only this text is displaying the
						character "@" which is configured as the only glyph in
						our font, and that text is styled to the appropriate
						icon's font
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>Rich Text</MCGuiTitle>
					<MCGuiText>This one is hard to explain...</MCGuiText>
					<MCGuiText>
						Had to partially reimplement the way minecraft handles
						translatable messages, our mod messages conform to the
						format used by FormatJS, making them integrate into this
						website kind of seamlessly
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>End portal background</MCGuiTitle>
					<MCGuiText>
						We literally backported the end portal shader from
						minecraft glsl ES 3 to glsl ES 1.0 and ran it on a
						canvas
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap='0'>
					<MCGuiTitle>React MCUI</MCGuiTitle>
					<MCGuiText>
						Im planning on releasing the code used to render this
						web page in Minecraft's UI aesthetic as an npm package,
						to be used for similar use cases or just for fun
					</MCGuiText>
					<MCGuiText />
					<MCGuiText>Coming soon?</MCGuiText>
				</Vertical>
			</MCGui>
		</Vertical>
	);
}

export default TechnologyPage;
