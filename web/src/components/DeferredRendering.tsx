import { PropsWithChildren, useEffect } from 'react';
import { useDisclosure } from '@mantine/hooks';

function DeferredRendering({ children }: PropsWithChildren<{}>) {
	const [mounted, handlers] = useDisclosure(false);

	useEffect(() => handlers.open(), []);

	if (!mounted) return null;

	return <>{children}</>;
}

export default DeferredRendering;
