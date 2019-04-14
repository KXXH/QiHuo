/**
 * Wrap of sub component which need use as Button capacity (like Icon component).
 * This helps accessibility reader to tread as a interactive button to operation.
 */
import * as React from 'react';
interface TransButtonProps extends React.HTMLAttributes<HTMLButtonElement> {
    onClick?: () => void;
}
declare class TransButton extends React.Component<TransButtonProps> {
    button?: HTMLButtonElement;
    lastKeyCode?: number;
    onKeyDown: React.KeyboardEventHandler<HTMLButtonElement>;
    onKeyUp: React.KeyboardEventHandler<HTMLButtonElement>;
    setRef: (btn: HTMLButtonElement) => void;
    focus(): void;
    blur(): void;
    render(): JSX.Element;
}
export default TransButton;
