import { useMemo, useState } from 'react';
import { ITEM_META } from '../itemMeta';
import { ItemMeta } from '../types';
import { Horizontal, Vertical } from '../Layout';
import { TextInput } from '@mantine/core';
import { ItemGrid } from '../components/ItemGrid';
import { useIntl } from 'react-intl';
import { itemToKey } from '../components/Item';
import { MCGui } from '../components/MCGui';
import { PixelScaling } from '../components/PixelScaling';
import { MCText } from '../components/MCText';
import { MCTextInput } from '../components/MCTextInput';
import { useLocation } from 'wouter';

export const ItemGallery = () => {
	const [filter, setFilter] = useState('');
	const intl = useIntl();
	const { scaling } = PixelScaling.use();
	const [, setLocation] = useLocation();

	const items = useMemo(
		() =>
			(ITEM_META.items as ItemMeta[]).map(
				(item) =>
					({
						...item,
						name: intl.formatMessage({ id: itemToKey(item.item) }),
					} as ItemMeta)
			),
		[intl]
	);

	const filteredItems = useMemo(
		() =>
			items.filter(
				(itemEntry) =>
					!filter ||
					[itemEntry.item, itemEntry.name].some((s) =>
						s.toLowerCase().includes(filter.toLocaleLowerCase())
					)
			),
		[items, filter]
	);

	return (
		<Horizontal justify='center'>
			<MCGui maw='800px' flex={1}>
				<Vertical p={scaling * 2}>
					<MCText c='dark' shadowColor='#9b9b9b'>
						Item Gallery
					</MCText>

					<MCTextInput
						value={filter}
						placeholder='filter'
						onChange={(e) => setFilter(e.currentTarget.value)}
					/>

					{filteredItems.length ? (
						<ItemGrid
							items={filteredItems}
							onItemClick={(item) => {
								console.log({ item });
								setLocation(`/item/${item.item}`);
							}}
						/>
					) : (
						<MCText>No matching items found...</MCText>
					)}
				</Vertical>
			</MCGui>
		</Horizontal>
	);
};
