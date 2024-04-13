import {
	Anchor,
	AppShell,
	Image,
	MantineProvider,
	createTheme,
} from '@mantine/core';
import { IntlProvider } from 'react-intl';

import EN_US from '../../src/main/resources/assets/echoes_of_the_elders/lang/en_us.json';
import defaultRichTextComponents from './defaultRichTextComponents';
import { PixelScaling } from './components/PixelScaling';
import ModTitleImage from '../../design/title_spiritual_awakening.png';
import { Routes } from './Routes';
import { BGCanvas } from './components/BGCanvas';
import { Link } from 'wouter';

const theme = createTheme({
	/** Your theme override here */
});

function App() {
	return (
		<IntlProvider
			messages={EN_US}
			locale='en-US'
			defaultLocale='en-US'
			defaultRichTextElements={defaultRichTextComponents}
		>
			<PixelScaling.Provider scaling={3}>
				<MantineProvider theme={theme}>
					<BGCanvas>
						<AppShell header={{ height: 120 }} padding='md'>
							<AppShell.Header
								style={{ border: 0, background: 'transparent' }}
							>
								<Anchor component={Link} to='/'>
									<Image
										src={ModTitleImage}
										h='120px'
										w='fit-content'
										p='md'
										ml='auto'
										mr='auto'
									/>
								</Anchor>
							</AppShell.Header>
							<AppShell.Main>
								<Routes />
							</AppShell.Main>
						</AppShell>
					</BGCanvas>
				</MantineProvider>
			</PixelScaling.Provider>
		</IntlProvider>
	);
}

export default App;
