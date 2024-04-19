import { MantineProvider, createTheme } from '@mantine/core';
import { IntlProvider } from 'react-intl';

import EN_US from '../../src/main/resources/assets/echoes_of_the_elders/lang/en_us.json';
import defaultRichTextComponents from './defaultRichTextComponents';
import { PixelScaling } from './components/PixelScaling';
import { Routes } from './Routes';
import { BGCanvas } from './components/BGCanvas';
import Shell from './components/Shell';

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
						<Shell>
							<Routes />
						</Shell>
					</BGCanvas>
				</MantineProvider>
			</PixelScaling.Provider>
		</IntlProvider>
	);
}

export default App;
