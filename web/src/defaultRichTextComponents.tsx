import { MCGlyphIcon } from './components/MCGlyphIcon';
import { ITEM_META } from './itemMeta';
import { MC_PALETTE } from './logic/MCPalette';

const NOOP = (chunks: any) => chunks;

const ICON_TEST = (iconId: string) => (chunks: any) =>
	(
		<>
			{hasIcon(iconId) && (
				<MCGlyphIcon font={'echoes_of_the_elders:' + iconId} />
			)}
			<span style={{ color: MC_PALETTE[iconId.toLocaleLowerCase()] }}>
				{chunks}
			</span>
		</>
	);

const UPPER_PALETTE_IDS = Object.keys(MC_PALETTE).map((s) => s.toUpperCase());
const hasIcon = (iconId: string) => {
	return !UPPER_PALETTE_IDS.includes(iconId);
};

const defaultRichTextComponents = Object.fromEntries(
	ITEM_META.richTextComponents.map((v) => [v, ICON_TEST(v)])
);

export default defaultRichTextComponents;
