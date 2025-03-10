import {CSSValue} from '../../syntax/parser';
import {CSSRadialGradientImage} from '../image';
import {Context} from '../../../core/context';

export declare const CLOSEST_SIDE = "closest-side";
export declare const FARTHEST_SIDE = "farthest-side";
export declare const CLOSEST_CORNER = "closest-corner";
export declare const FARTHEST_CORNER = "farthest-corner";
export declare const CIRCLE = "circle";
export declare const ELLIPSE = "ellipse";
export declare const COVER = "cover";
export declare const CONTAIN = "contain";
export declare const radialGradient: (context: Context, tokens: CSSValue[]) => CSSRadialGradientImage;
