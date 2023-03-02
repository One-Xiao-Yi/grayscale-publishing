/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { hasLogin?: boolean } | undefined) {
  const hasLogin = initialState?.hasLogin ?? undefined;
  return {
    canAdmin: hasLogin,
  };
}
