/**<pre>
 此包用来实现权限相关操作.
 权限包含几个要素：
 用户、用户组、角色、操作（树形）(包含两大类规则:access:deny)、判断规则
 权限判断如下：
 1、用户组不能嵌套
 2、权限判断流程：先取进行deny规则判断，判断返回null或false再进行accept规则判断。否则直接判断无权限。
 3、判断登录者权限时，先进行角色循环，再进行群组的角色进行循环。
 4、Operation权限判断时，根据 {@link com.koron.ebs.permission.Operation#isInherit() 继承权限}来判断是否要先进上级操作进行判断
 5、
 <svg viewBox="0 0 600 300" xml:space="preserve" color-interpolation-filters="sRGB"
                class="st12">
<style type="text/css">
	<![CDATA[
		.st1 {visibility:visible}
		.st2 {fill:#5b9bd5;fill-opacity:0.22;filter:url(#filter_2);stroke:#5b9bd5;stroke-opacity:0.22}
		.st3 {fill:#5b9bd5;stroke:#c7c8c8;stroke-width:0.25}
		.st4 {fill:#feffff;font-family:黑体;font-size:1.00001em}
		.st5 {marker-end:url(#mrkr1-24);stroke:#5b9bd5;stroke-dasharray:7,5;stroke-linecap:round;stroke-linejoin:round;stroke-width:1}
		.st6 {fill:#5b9bd5;fill-opacity:1;stroke:#5b9bd5;stroke-opacity:1;stroke-width:0.28409090909091}
		.st7 {fill:#ffffff;stroke:none;stroke-linecap:butt;stroke-width:7.2}
		.st8 {fill:#4f87bb;font-family:Calibri;font-size:0.666664em}
		.st9 {font-family:黑体;font-size:1em}
		.st10 {stroke:#5b9bd5;stroke-linecap:round;stroke-linejoin:round;stroke-width:1}
		.st11 {font-size:1em}
		.st12 {fill:none;fill-rule:evenodd;font-size:12px;overflow:visible;stroke-linecap:square;stroke-miterlimit:3}
	]]>
	</style>

	<defs id="Markers">
		<g id="lend1">
			<path d="M 1 -1 L 0 0 L 1 1 " style="stroke-linecap:round;stroke-linejoin:round;fill:none"/>
		</g>
		<marker id="mrkr1-24" class="st6" orient="auto" markerUnits="strokeWidth" overflow="visible">
			<use xlink:href="#lend1" transform="scale(-3.52,-3.52) "/>
		</marker>
	</defs>
	<defs id="Filters">
		<filter id="filter_2">
			<feGaussianBlur stdDeviation="2"/>
		</filter>
	</defs>
	<g>
		<title>权限模块</title>
		<g id="shape1-1" transform="translate(18,-340.157)">
			<title>用例</title>
			<desc>账号</desc>
			<g id="shadow1-2" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">账号</text>		</g>
		<g id="shape2-7" transform="translate(113.386,-521.575)">
			<title>用例.11</title>
			<desc>角色</desc>
			<g id="shadow2-8" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">角色</text>		</g>
		<g id="shape3-13" transform="translate(226.772,-348.661)">
			<title>用例.12</title>
			<desc>群组</desc>
			<g id="shadow3-14" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">群组</text>		</g>
		<g id="shape5-19" transform="translate(227.287,-368.504)">
			<title>包含.15</title>
			<desc>&#60;&#60;包含&#62;&#62;</desc>
			<path d="M0 600.4 L-96.42 604.33" class="st5"/>
			<rect x="-64.1772" y="597.037" width="31.9378" height="10.6501" class="st7"/>
			<text x="-64.18" y="605.03" class="st8">&#60;&#60;<tspan class="st9">包含</tspan>&#62;&#62;</text>		</g>
		<g id="shape6-28" transform="translate(162.992,-470.551)">
			<title>包含.16</title>
			<desc>&#60;&#60;包含&#62;&#62;</desc>
			<path d="M7.09 595.28 L7.09 544.25" class="st5"/>
			<rect x="-8.88218" y="564.439" width="31.9378" height="10.6501" class="st7"/>
			<text x="-8.88" y="572.43" class="st8">&#60;&#60;<tspan class="st9">包含</tspan>&#62;&#62;</text>		</g>
		<g id="shape7-36" transform="translate(340.157,-532.913)">
			<title>用例.17</title>
			<desc>操作</desc>
			<g id="shadow7-37" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">操作</text>		</g>
		<g id="shape8-42" transform="translate(226,-537.165)">
			<title>关联</title>
			<path d="M0 591.06 L114.93 585.32" class="st10"/>
		</g>
		<g id="shape9-45" transform="translate(360,-419.528)">
			<title>用例.21</title>
			<desc>规则</desc>
			<g id="shadow9-46" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">规则</text>		</g>
		<g id="shape10-51" transform="translate(399.685,-532.937)">
			<title>包含.24</title>
			<desc>&#60;&#60;包含&#62;&#62; deny,access</desc>
			<path d="M0.14 595.28 L14.04 674.69" class="st5"/>
			<rect x="-12.333" y="624.859" width="38.8394" height="20.2497" class="st7"/>
			<text x="-8.88" y="632.85" class="st8">&#60;&#60;<tspan class="st9">包含</tspan>&#62;&#62; <tspan x="-12.33" dy="1.233em"
						class="st11">deny</tspan>,access</text>		</g>
		<g id="shape20-60" transform="translate(113.386,-436.535)">
			<title>用例.20</title>
			<desc>用户</desc>
			<g id="shadow20-61" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
				<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
			</g>
			<ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
			<text x="44.69" y="582.26" class="st4">用户</text>		</g>
		<g id="shape22-66" transform="translate(262.999,-381.53)">
			<title>扩展</title>
			<desc>&#60;&#60;扩展&#62;&#62;</desc>
			<path d="M0 595.28 L-72.45 539.12" class="st5"/>
			<rect x="-52.1959" y="561.875" width="31.9378" height="10.6501" class="st7"/>
			<text x="-52.2" y="569.86" class="st8">&#60;&#60;<tspan class="st9">扩展</tspan>&#62;&#62;</text>		</g>
		<g id="shape24-74" transform="translate(90.8295,-373.47)">
			<title>扩展.24</title>
			<desc>&#60;&#60;扩展&#62;&#62;</desc>
			<path d="M0 595.28 L63.11 531.51" class="st5"/>
			<rect x="15.5876" y="558.066" width="31.9378" height="10.6501" class="st7"/>
			<text x="15.59" y="566.05" class="st8">&#60;&#60;<tspan class="st9">扩展</tspan>&#62;&#62;</text>		</g>
	</g>
</svg>
 模块关系图如下：
 <svg   viewBox="0 0 600 200" xml:space="preserve" color-interpolation-filters="sRGB"
                class="st6">
        <style type="text/css">
        <![CDATA[
                .st1 {visibility:visible}
                .st2 {fill:#5b9bd5;fill-opacity:0.22;filter:url(#filter_2);stroke:#5b9bd5;stroke-opacity:0.22}
                .st3 {fill:#5b9bd5;stroke:#c7c8c8;stroke-width:0.25}
                .st4 {fill:#feffff;font-family:黑体;font-size:1.00001em}
                .st5 {stroke:#5b9bd5;stroke-linecap:round;stroke-linejoin:round;stroke-width:1}
                .st6 {fill:none;fill-rule:evenodd;font-size:12px;overflow:visible;stroke-linecap:square;stroke-miterlimit:3}
        ]]>
        </style>

        <defs id="Filters">
                <filter id="filter_2">
                        <feGaussianBlur stdDeviation="2"/>
                </filter>
        </defs>
        <g>
                <title>权限（操作关系示例图）</title>
                <g id="shape1-1" transform="translate(181.417,-535.748)">
                        <title>用例.26</title>
                        <desc>基础资料</desc>
                        <g id="shadow1-2" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
                                <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
                        </g>
                        <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
                        <text x="32.69" y="582.26" class="st4">基础资料</text>              </g>
                <g id="shape2-7" transform="translate(110.551,-479.055)">
                        <title>用例.27</title>
                        <desc>员工资料</desc>
                        <g id="shadow2-8" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
                                <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
                        </g>
                        <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
                        <text x="32.69" y="582.26" class="st4">员工资料</text>              </g>
                <g id="shape3-13" transform="translate(266.457,-479.055)">
                        <title>用例.28</title>
                        <desc>部门资料</desc>
                        <g id="shadow3-14" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
                                <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
                        </g>
                        <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
                        <text x="32.69" y="582.26" class="st4">部门资料</text>              </g>
                <g id="shape4-19" transform="translate(28.3465,-422.362)">
                        <title>用例.29</title>
                        <desc>新增</desc>
                        <g id="shadow4-20" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
                                <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
                        </g>
                        <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
                        <text x="44.69" y="582.26" class="st4">新增</text>                </g>
                <g id="shape5-25" transform="translate(158.74,-422.362)">
                        <title>用例.30</title>
                        <desc>修改</desc>
                        <g id="shadow5-26" transform="matrix(1,0,0,1,0.345598,1.97279)" class="st1">
                                <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st2"/>
                        </g>
                        <ellipse cx="56.6929" cy="578.268" rx="56.6929" ry="17.0079" class="st3"/>
                        <text x="44.69" y="582.26" class="st4">修改</text>                </g>
                <g id="shape6-31" transform="translate(218.204,-536.831)">
                        <title>关联.32</title>
                        <path d="M0 595.28 L-31.05 620.12" class="st5"/>
                </g>
                <g id="shape7-34" transform="translate(261.375,-537.246)">
                        <title>关联.33</title>
                        <path d="M0 595.28 L38.51 620.95" class="st5"/>
                </g>
                <g id="shape8-37" transform="translate(144.63,-480.467)">
                        <title>关联.34</title>
                        <path d="M0 595.28 L-36.98 620.78" class="st5"/>
                </g>
                <g id="shape9-40" transform="translate(181.253,-479.583)">
                        <title>关联.35</title>
                        <path d="M0 595.28 L20.17 619.01" class="st5"/>
                </g>
        </g>
</svg>
 </pre>
 * 
 */
package com.koron.ebs.permission;