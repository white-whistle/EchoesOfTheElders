import { Box } from '@mantine/core';
import { Vertical } from '../Layout';
import { MCGui, MCGuiText, MCGuiTitle } from '../components/MCGui';
import { PixelScaling } from '../components/PixelScaling';
import MCSoiler from '../components/MCSpoiler';
import BorderImage from '../components/BorderImage';
import TweenImage from '../components/TweenImage';
import { basePath } from '../util';

function GettingStarted() {
	const { px } = PixelScaling.use();

	return (
		<Vertical w='100%' align='center'>
			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap={0}>
					<MCGuiTitle>Getting Started</MCGuiTitle>
					<MCGuiText>Welcome to Echoes of the Elders!</MCGuiText>
					<MCGuiText />
					<MCGuiText>
						Some sections of this page contain spoilers! So we have
						conveniently covered them in spoiler tags, click on a
						spoiler tag to reveal its contents!
					</MCGuiText>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap={0}>
					<MCGuiTitle>Core mechanics</MCGuiTitle>
					<MCGuiText>
						Some integral features present in the mod we felt like
						might need a little explanation
					</MCGuiText>
					<MCGuiText />
					<MCGuiTitle>Upgrading artifacts</MCGuiTitle>
					<MCGuiText>
						Relics can be upgraded via combining two relics of the
						same type (right click artifact on artifact)
					</MCGuiText>
					<BorderImage
						src={`${basePath}/upgrade_relic_relic.gif?raw=true`}
					/>

					<MCGuiText />
					<MCGuiText>
						Relics can also be upgraded using upgrade items like
						hammers (right click hammer on artifact)
					</MCGuiText>

					<BorderImage
						src={`${basePath}/upgrade_relic_hammer.gif?raw=true`}
					/>
					<MCGuiText />
					<MCGuiText>
						Relics change their appearance every 16 levels (swag)
					</MCGuiText>
					<BorderImage
						src={`${basePath}/upgrade_relic_max.gif?raw=true`}
					/>

					<MCGuiText />
					<MCGuiTitle>Toggled items</MCGuiTitle>
					<MCGuiText>
						Some items can be toggled ON/OFF (right click item)
					</MCGuiText>
					<BorderImage
						src={`${basePath}/toggle_relic.gif?raw=true`}
					/>
				</Vertical>
			</MCGui>

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={px(2)} gap={px(4)}>
					<Vertical gap={0}>
						<MCGuiTitle>Progression</MCGuiTitle>
						<MCGuiText />
					</Vertical>

					<MCSoiler label='Dimension Access - step 1'>
						<MCGuiTitle>Meet the Elder Man</MCGuiTitle>
						<MCGuiText>
							A higher order being from higher dimensions, can
							travel to farther dimensions from the origin
						</MCGuiText>
						<MCGuiText>
							Makes you wonder how he is able to shift between
							dimensions...
						</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/elderman.png?raw=true`}
						/>
					</MCSoiler>

					<MCSoiler label='Dimension Access - step 2'>
						<MCGuiTitle>Kill the Elder Man</MCGuiTitle>
						<MCGuiText>...</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/doomstick.png?raw=true`}
						/>
					</MCSoiler>

					<MCSoiler label='Dimension Access - step 3'>
						<MCGuiTitle>Obtain the Elder Prism</MCGuiTitle>
						<MCGuiText>
							This must be how the elder man is able to shift
							between dimensions, although you are not confident
							you can get it to work like he did
						</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/prism.png?raw=true`}
						/>
					</MCSoiler>

					<MCSoiler label='Dimension Access - step 4'>
						<MCGuiTitle>Enter the Spirit Realm</MCGuiTitle>
						<MCGuiText>
							Using the Elder Prism shift into the Spirit Realm
						</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/spirit_realm.png?raw=true`}
						/>
						<MCGuiText />
						<MCGuiText>
							The spirit realm's terrain is identical to that of
							the overworld, you can abuse this to double ores,
							build an interdimensional base, or fall to your
							death!
						</MCGuiText>
						<Box pos='relative'>
							<BorderImage
								src={`${basePath}/screenshots/terrain_overworld.png?raw=true`}
							/>
							<TweenImage
								src={`${basePath}/screenshots/terrain_spirit_realm.png?raw=true`}
								pos='absolute'
								top={0}
								left={0}
							/>
						</Box>
					</MCSoiler>

					<MCSoiler label='Keys'>
						<MCGuiTitle>Obtain old keys</MCGuiTitle>
						<MCGuiText>
							Mysterious spirits fly around in this dimension,
							some holding food, while others old looking keys..
						</MCGuiText>
						<MCGuiText>Wonder what the keys can open...</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/key_spirit.png?raw=true`}
						/>
					</MCSoiler>

					<MCSoiler label='Caches'>
						<MCGuiTitle>
							Obtain a common artifact from a Cache
						</MCGuiTitle>
						<MCGuiText>
							Open the cache by right clicking on it with an old
							key
						</MCGuiText>
						<MCGuiText>
							The rewards will fall in a Soulbound bag, that can
							only be picked up and opened by the player that
							opened the cache
						</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/cache.png?raw=true`}
						/>
					</MCSoiler>

					<MCSoiler label='Raids'>
						<MCGuiTitle>Obtain artifacts from a Raid</MCGuiTitle>
						<MCGuiText>
							Raids are the main source of artifacts, each
							artifact drops in a specific raid level range (cache
							artifacts are technically Raid level 1 rewards)
						</MCGuiText>
						<MCGuiText>
							Every 5 raid waves, the players in the raid get one
							artifact, and the raid level is raised. Tougher foes
							appear at higher raid levels.
						</MCGuiText>
						<MCGuiText />
						<MCGuiText>
							Locate a raid shrine, interact with the shrine's
							prism surface using the elder prism. this will
							awaken the shrine and summon the raid totem.
						</MCGuiText>
						<Box pos='relative'>
							<BorderImage
								src={`${basePath}/screenshots/totem_spawn.png?raw=true`}
							/>
							<TweenImage
								src={`${basePath}/screenshots/totem_entity.png?raw=true`}
								pos='absolute'
								top={0}
								left={0}
							/>
						</Box>

						<MCGuiText />
						<MCGuiText>
							Begin the raid by using an old key on the raid
							totem, this will open a brief period (30s) where
							other players in the area can also insert an old key
							into the raid totem.
						</MCGuiText>
						<MCGuiText>
							Only players with keys inside the totem get rewards
							after the raid wave is complete!
						</MCGuiText>
						<MCGuiText>
							Players can choose how long they want to stay in a
							raid after each wave is complete. Extracting secures
							the rewards obtained so far but exist the raid,
							while continuing opens the possibility to greater
							rewards while risking all of the rewards obtained so
							far.
						</MCGuiText>
						<BorderImage
							src={`${basePath}/screenshots/raid_active.png?raw=true`}
						/>
						<MCGuiText />
						<MCGuiText>
							When the raid totem dies, the raid fails - failed
							raids do not drop rewards at the end of the raid.
						</MCGuiText>
						<MCGuiText>
							After the raid has failed, the totem will turn into
							a dormant shrine - to be activated once more.
						</MCGuiText>

						<MCGuiText />
						<MCGuiText>
							Raids scale infinitely! how long can you last?
						</MCGuiText>
					</MCSoiler>
				</Vertical>
			</MCGui>
		</Vertical>
	);
}

export default GettingStarted;
