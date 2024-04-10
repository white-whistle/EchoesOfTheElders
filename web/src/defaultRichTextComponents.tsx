import { ITEM_META } from './itemMeta';

const NOOP = (chunks: any) => chunks;

const defaultRichTextComponents = Object.fromEntries(
	ITEM_META.richTextComponents.map((v) => [v, NOOP])
);

export default defaultRichTextComponents;
