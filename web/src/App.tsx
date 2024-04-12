import { AppShell, Image, MantineProvider, createTheme } from '@mantine/core';
import { IntlProvider } from 'react-intl';

import EN_US from '../../src/main/resources/assets/echoes_of_the_elders/lang/en_us.json';
import defaultRichTextComponents from './defaultRichTextComponents';
import { PixelScaling } from './components/PixelScaling';
import ModTitleImage from '../../design/title_spiritual_awakening.png';
import { Routes } from './Routes';

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
					<AppShell header={{ height: 120 }} padding='md'>
						<AppShell.Header>
							<Image
								src={ModTitleImage}
								h='120px'
								w='fit-content'
								p='md'
								ml='auto'
								mr='auto'
							/>
						</AppShell.Header>
						<AppShell.Main>
							<Routes />
						</AppShell.Main>
					</AppShell>
				</MantineProvider>
			</PixelScaling.Provider>
		</IntlProvider>
	);
}

export default App;
