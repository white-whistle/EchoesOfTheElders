import { Vertical } from './Layout';
import { ItemGrid } from './ItemGrid';
import {
	AppShell,
	MantineProvider,
	TextInput,
	createTheme,
} from '@mantine/core';
import { IntlProvider } from 'react-intl';

import { useMemo, useState } from 'react';
import { ItemMeta } from './Item';

import ITEM_META from '../../scripts/dist/artifactItemMetadata.json';
import EN_US from '../../src/main/resources/assets/echoes_of_the_elders/lang/en_us.json';

const theme = createTheme({
	/** Your theme override here */
});

function App() {
	const [filter, setFilter] = useState('');

	const items = useMemo(
		() =>
			(ITEM_META as ItemMeta[]).filter(
				(itemEntry) => !filter || itemEntry.item.includes(filter)
			),
		[filter]
	);

	return (
		<IntlProvider messages={EN_US} locale='en-US' defaultLocale='en-US'>
			<MantineProvider theme={theme}>
				<AppShell header={{ height: 60 }} padding='md'>
					<AppShell.Header>EOTE</AppShell.Header>
					<AppShell.Main>
						<Vertical>
							<p>EOTE items</p>
							<TextInput
								value={filter}
								placeholder='filter'
								onChange={(e) =>
									setFilter(e.currentTarget.value)
								}
							/>
							<ItemGrid items={items} itemSize={18 * 4} />
						</Vertical>
					</AppShell.Main>
				</AppShell>
			</MantineProvider>
		</IntlProvider>
	);
}

export default App;
