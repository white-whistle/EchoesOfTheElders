import { createContext } from '@sgty/kontext-react';
import { useMemo } from 'react';

export const PixelScaling = createContext(
	({ scaling }: { scaling: number }) => {
		return useMemo(
			() => ({ scaling, px: (v: number) => `${scaling * v}px` }),
			[scaling]
		);
	}
);
