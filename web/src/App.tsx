import { Vertical } from './Layout';
import { ItemGrid } from './ItemGrid';
import {
	AppShell,
	Input,
	MantineProvider,
	TextInput,
	createTheme,
} from '@mantine/core';

import ITEM_META from '../../scripts/dist/artifactItemMetadata.json';
import { useMemo, useState } from 'react';

const theme = createTheme({
	/** Your theme override here */
});

function App() {
	const [filter, setFilter] = useState('');

	const items = useMemo(
		() => ITEM_META.filter((item) => !filter || item.item.includes(filter)),
		[filter]
	);

	return (
		<MantineProvider theme={theme}>
			<AppShell header={{ height: 60 }} padding='md'>
				<AppShell.Header>EOTE</AppShell.Header>
				<AppShell.Main>
					<Vertical>
						<p>EOTE items</p>
						<TextInput
							value={filter}
							placeholder='filter'
							onChange={(e) => setFilter(e.currentTarget.value)}
						/>
						<ItemGrid items={items} />
					</Vertical>
				</AppShell.Main>
			</AppShell>
		</MantineProvider>
	);
}

export default App;
