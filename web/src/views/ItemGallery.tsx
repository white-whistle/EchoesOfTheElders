import { useMemo, useState } from 'react';
import { ITEM_META } from '../itemMeta';
import { ItemMeta } from '../types';
import { Horizontal, Vertical } from '../Layout';
import { ItemGrid } from '../components/ItemGrid';
import { FormattedMessage, useIntl } from 'react-intl';
import { itemToKey } from '../components/Item';
import { MCGui, MCGuiTitle } from '../components/MCGui';
import { PixelScaling } from '../components/PixelScaling';
import { MCText } from '../components/MCText';
import { MCTextInput } from '../components/MCTextInput';
import { useLocation } from 'wouter';
import { MCGlyphIcon } from '../components/MCGlyphIcon';
import { MCTooltip } from '../components/MCTooltip';
import { DropMapGraph } from '../components/DropMapGraph';
import { Box } from '@mantine/core';

const tooltipInfo = `\
Smart filters
<GRAY>write the following in the filter to filter by item metadata</GRAY>

min drop level:
<GOLD>$dlMin:<BLUE>number</BLUE></GOLD>

max drop level:
<GOLD>$dlMax:<BLUE>number</BLUE></GOLD>

rarity:
<GOLD>$rarity:<BLUE>string</BLUE></GOLD>

reward:
<GOLD>$reward</GOLD>

artifacts:
<GOLD>$artifact</GOLD>\
`;

const filterHandlers = {
	$dlMin: (arg: string) => {
		const n = Number(arg);
		if (isNaN(n)) return null;

		return (item: ItemMeta) => (item.dropData?.max ?? Infinity) >= n;
	},

	$dlMax: (arg: string) => {
		const n = Number(arg);
		if (isNaN(n)) return null;

		return (item: ItemMeta) => (item.dropData?.min ?? 0) <= n;
	},

	$artifact: () => {
		return (item: ItemMeta) => item.isArtifact;
	},

	$reward: () => {
		return (item: ItemMeta) => item.isReward;
	},

	$rarity: (arg: string) => {
		return (item: ItemMeta) => (item.rarity ?? '').includes(arg);
	},
};

const smartFilterRegex = /(?<func>\$\w*):?(?<arg>[\S]+?)?(?: |$)/g;

function useRichFilter(filter: string) {
	return useMemo(() => {
		const matches = [...filter.matchAll(smartFilterRegex)];
		let remainder = filter;
		matches.forEach((m) => {
			remainder = remainder.replace(m[0], '');
		});
		remainder.trim();

		const predicates: ((item: ItemMeta) => boolean)[] = [];

		predicates.push(
			(item: ItemMeta) =>
				!remainder ||
				[item.item, item.name].some((s) =>
					s.toLowerCase().includes(remainder.toLocaleLowerCase())
				)
		);

		matches.forEach((m) => {
			const func = m[1];
			const arg = m[2];

			const predicateFactory = filterHandlers[func];
			if (!predicateFactory) return;

			const predicate = predicateFactory(arg);
			if (!predicate) return;

			predicates.push(predicate);
		});

		return (item: ItemMeta) => {
			return predicates.every((p) => p(item));
		};
	}, [filter]);
}

export const ItemGallery = () => {
	const [filter, setFilter] = useState('');
	const [highlight, setHighlight] = useState('');
	const intl = useIntl();
	const { scaling } = PixelScaling.use();
	const [, setLocation] = useLocation();

	const richFilter = useRichFilter(filter);
	const richFilterHighlight = useRichFilter(highlight);

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
		() => items.filter(richFilter),
		[items, richFilter]
	);

	const highlightedItems = useMemo(
		() => filteredItems.filter(richFilterHighlight),
		[filteredItems, richFilterHighlight]
	);

	const infoSection = useMemo(
		() => (
			<MCTooltip
				position='bottom'
				label={
					<Vertical gap='0'>
						<MCText
							style={{
								whiteSpace: 'pre-wrap',
							}}
						>
							<FormattedMessage
								id='inline'
								defaultMessage={tooltipInfo}
							/>
						</MCText>
					</Vertical>
				}
			>
				<MCGlyphIcon
					font='echoes_of_the_elders:info'
					mr={scaling * 6}
				/>
			</MCTooltip>
		),
		[scaling]
	);

	return (
		<Vertical w='100%' align='center'>
			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={scaling * 2}>
					<MCGuiTitle>Item Gallery</MCGuiTitle>

					<MCTextInput
						value={filter}
						placeholder='filter'
						onChange={(e) => setFilter(e.currentTarget.value)}
						rightSection={infoSection}
					/>

					<MCTextInput
						value={highlight}
						placeholder='highlight'
						onChange={(e) => setHighlight(e.currentTarget.value)}
						rightSection={infoSection}
					/>

					{filteredItems.length ? (
						<ItemGrid
							items={filteredItems}
							highlight={highlightedItems}
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

			<MCGui w='100%' maw='800px' flex={1}>
				<Vertical p={scaling * 2}>
					<MCGuiTitle>Drop Map</MCGuiTitle>

					<Box w='100%' h='400px'>
						<DropMapGraph items={filteredItems} />
					</Box>
				</Vertical>
			</MCGui>
		</Vertical>
	);
};
