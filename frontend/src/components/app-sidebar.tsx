import { NavLink } from "@/components/NavLink";
import { useLocation } from "react-router-dom";
import { Boxes, Users, LayoutDashboard, Wrench, LogOut, Settings, Building2 } from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";

const main = [
  { title: "Overview", url: "/app", icon: LayoutDashboard, end: true },
  { title: "Stock", url: "/app/stock", icon: Boxes },
  { title: "Users", url: "/app/users", icon: Users },
];

const enterprise = [
  { title: "Enterprise", url: "/app/enterprise", icon: Building2 },
  { title: "Settings", url: "/app/settings", icon: Settings },
];

export function AppSidebar() {
  const { state } = useSidebar();
  const collapsed = state === "collapsed";
  const location = useLocation();

  const linkCls =
    "flex items-center gap-3 rounded-md px-3 py-2 text-sm transition-colors hover:bg-sidebar-accent/70 hover:text-sidebar-accent-foreground";
  const activeCls = "bg-sidebar-accent text-sidebar-accent-foreground font-medium shadow-sm";

  return (
    <Sidebar collapsible="icon" className="border-r border-sidebar-border">
      <SidebarHeader className="border-b border-sidebar-border">
        <div className="flex items-center gap-2 px-2 py-3">
          <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-lg bg-gradient-accent shadow-glow">
            <Wrench className="h-4 w-4 text-primary-foreground" strokeWidth={2.5} />
          </div>
          {!collapsed && (
            <div className="flex flex-col leading-tight animate-fade-in">
              <span className="font-display text-base font-bold text-sidebar-foreground">Forge</span>
              <span className="text-[10px] uppercase tracking-widest text-sidebar-foreground/60">
                Workshop OS
              </span>
            </div>
          )}
        </div>
      </SidebarHeader>

      <SidebarContent className="px-2 py-4">
        <SidebarGroup>
          {!collapsed && <SidebarGroupLabel>Workspace</SidebarGroupLabel>}
          <SidebarGroupContent>
            <SidebarMenu>
              {main.map((item) => (
                <SidebarMenuItem key={item.url}>
                  <SidebarMenuButton asChild tooltip={item.title}>
                    <NavLink to={item.url} end={item.end} className={linkCls} activeClassName={activeCls}>
                      <item.icon className="h-4 w-4 shrink-0" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        <SidebarGroup className="mt-4">
          {!collapsed && <SidebarGroupLabel>Enterprise</SidebarGroupLabel>}
          <SidebarGroupContent>
            <SidebarMenu>
              {enterprise.map((item) => (
                <SidebarMenuItem key={item.url}>
                  <SidebarMenuButton asChild tooltip={item.title}>
                    <NavLink to={item.url} className={linkCls} activeClassName={activeCls}>
                      <item.icon className="h-4 w-4 shrink-0" />
                      {!collapsed && <span>{item.title}</span>}
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      <SidebarFooter className="border-t border-sidebar-border p-2">
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton asChild tooltip="Sign out">
              <NavLink to="/login" className={linkCls}>
                <LogOut className="h-4 w-4 shrink-0" />
                {!collapsed && <span>Sign out</span>}
              </NavLink>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
        {!collapsed && (
          <div className="mt-2 rounded-lg bg-sidebar-accent/50 p-3 text-xs text-sidebar-foreground/80">
            <div className="font-medium text-sidebar-foreground">Acme Workshop</div>
            <div className="text-sidebar-foreground/60">Pro Plan · 12 seats</div>
          </div>
        )}
        <div className="px-2 pt-2 text-[10px] text-sidebar-foreground/40">
          {location.pathname}
        </div>
      </SidebarFooter>
    </Sidebar>
  );
}
